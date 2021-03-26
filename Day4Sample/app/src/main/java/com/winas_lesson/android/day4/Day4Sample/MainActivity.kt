package com.winas_lesson.android.day4.Day4Sample

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.Day4Sample.data.local.Me
import com.winas_lesson.android.day4.Day4Sample.data.model.Content
import com.winas_lesson.android.day4.Day4Sample.data.repository.Repository
import com.winas_lesson.android.day4.Day4Sample.databinding.ActivityMainBinding
import com.winas_lesson.android.day4.Day4Sample.databinding.ContentItemViewBinding
import com.winas_lesson.android.day4.Day4Sample.interfaces.ViewBindable
import com.winas_lesson.android.day4.Day4Sample.ui.AbstractActivity
import com.winas_lesson.android.day4.Day4Sample.util.jsonToList
import com.winas_lesson.android.day4.Day4Sample.util.jsonToObject
import com.winas_lesson.android.day4.Day4Sample.util.readStringFromAssetFile
import com.winas_lesson.android.day4.Day4Sample.util.showToast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.properties.Delegates


class MainActivity : AbstractActivity(), ViewBindable {
    override lateinit var binding: ViewBinding
    private val button: Button?
        get() = (binding as? ActivityMainBinding)?.button
    private val adapter by lazy { ContentListAdapter(applicationContext) }
    private val recyclerView: RecyclerView?
        get() = (binding as? ActivityMainBinding)?.recyclerView
    private lateinit var layoutManager: LinearLayoutManager

    private var contents: List<Content> by Delegates.observable(listOf()) { _, _, _ ->
        adapter.contents = contents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView?.adapter = adapter
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager

        // (2) load from master

//        // load simple string
//        var simpleString = readStringFromAssetFile("sample-text.txt")
//        Timber.d("[TEXT] content data= ${simpleString}")
//
//        // load data single
//        val jsonSingleString = readStringFromAssetFile("sample-single.json")
//        jsonSingleString.jsonToObject<Content>()?.let { content ->
//            Timber.d("[SINGLE] content data= ${content.debugDescription}")
//        }
//
//        // load data array
//        val jsonListString = readStringFromAssetFile("sample-list.json")
//        jsonListString.jsonToList<Content>()?.let { contents ->
//            contents.forEach { content ->
//                Timber.d("[LIST] content data= ${content.debugDescription}")
//            }
//        }

//        loadContentsFromMaster {
//            Timber.d("loadContentsFromMaster complete!!")
//            // fetch from local database using room
//            GlobalScope.launch {
//                Repository.localDb.contentDao().getAllContents().collect {
//                    it.forEach { content ->
//                        Timber.d("[CACHE] content data= ${content.debugDescription}")
//                    }
//                }
//            }
//        }

//        Repository.content.getSingle(
//            completion = {
//                Timber.d("[SERVER] single content data= ${it.debugDescription}")
//            },
//            failure = {
//                Timber.d("[SERVER] single content error!!")
//            }
//        )
//        Repository.content.getList(
//            completion = { contents ->
//                contents.forEach { content ->
//                    Timber.d("[SERVER] list content data= ${content.debugDescription}")
//                }
//            },
//            failure = {
//                Timber.d("[SERVER] list content error!!")
//            }
//        )
//        Repository.content.getListWithErrorCode(
//            completion = { contents, total, errorCode ->
//                Timber.d("[SERVER] list total= $total, errorCode= $errorCode")
//                contents.forEach { content ->
//                    Timber.d("[SERVER] list content data= ${content.debugDescription}")
//                }
//            },
//            failure = {
//                Timber.d("[SERVER] list content error!!")
//            }
//        )

        button?.setOnClickListener {
            loadContentsFromServer()
        }

        if (Me.isFirstLaunch) {
            loadContentsFromMaster {
                Timber.d("loadContentsFromMaster complete!!")
                showContentsFromCache()
            }
        } else {
            showContentsFromCache()
        }

        Handler().postDelayed({
            loadContentsFromServer()
        }, 5000L)
    }

    private fun loadContentsFromMaster(completion: (() -> Unit)) {
        GlobalScope.launch {
            // load data array
            val jsonListString = readStringFromAssetFile("sample-list.json")
            val contents = jsonListString.jsonToList<Content>() ?: listOf()
            // delete all cached contents from local database
            Repository.localDb.contentDao().deleteAll()
            contents.forEach { content ->
                Timber.d("[LIST] content data= ${content.debugDescription}")
            }
            // save in local database as cache
            Repository.localDb.contentDao().addAll(contents)
            Handler(Looper.getMainLooper()).post {
                completion()
            }
        }
    }

    private fun showContentsFromCache() {
        // fetch from local database using room
        GlobalScope.launch {
            Repository.localDb.contentDao().getAllContents().collect { contents ->
                contents.forEach { content ->
                    Timber.d("[CACHE] content data= ${content.debugDescription}")
                }
                Handler(Looper.getMainLooper()).post {
                    this@MainActivity.contents = contents
                    showToast("キャッシュからコンテンツを表示しました！")
                }
            }
        }
    }

    private fun loadContentsFromServer() {
        Repository.content.getList(
            completion = { contents ->
                contents.forEach { content ->
                    Timber.d("[SERVER] list content data= ${content.debugDescription}")
                }
                GlobalScope.async {
                    Repository.localDb.contentDao().deleteAll()
                    Repository.localDb.contentDao().addAll(contents)
                }
                showToast("サーバからコンテンツを表示しました！")
            },
            failure = {
                Timber.d("[SERVER] list content error!!")
            }
        )
    }
}

class ContentListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var contents: List<Content> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = ContentItemViewHolder.create(parent)
        return holder
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ContentItemViewHolder)?.content = contents[position]
    }
    override fun getItemCount(): Int {
        return contents.size
    }
    override fun getItemViewType(position: Int): Int {
        return 0
    }
}

class ContentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ContentItemViewBinding
    companion object {
        fun create(parent: ViewGroup): ContentItemViewHolder {
            // snake_case, UpperCamelCase, lowerCamelCase
            val viewBinding = ContentItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            val holder = ContentItemViewHolder(viewBinding.root)
            holder.binding = viewBinding
            return holder
        }
    }
    var content: Content by Delegates.observable(Content()) { prop, _, _ ->
        updateView()
    }
    private fun updateView() {
        binding.nameLabel.text = content.name
        binding.addressLabel.text = content.address
    }
}
