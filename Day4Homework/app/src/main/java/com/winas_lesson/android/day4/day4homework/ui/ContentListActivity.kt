package com.winas_lesson.android.day4.day4homework.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.day4homework.data.local.Me
import com.winas_lesson.android.day4.day4homework.data.model.Content
import com.winas_lesson.android.day4.day4homework.data.repository.Repository
import com.winas_lesson.android.day4.day4homework.databinding.ActivityContentListBinding
import com.winas_lesson.android.day4.day4homework.databinding.ContentItemViewBinding
import com.winas_lesson.android.day4.day4homework.interfaces.ViewBindable
import com.winas_lesson.android.day4.day4homework.util.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.properties.Delegates

class ContentListActivity : AbstractActivity(), ViewBindable {
    override lateinit var binding: ViewBinding

    private val accountButton: Button?
        get() = (binding as? ActivityContentListBinding)?.accountButton
    private val reloadButton: Button?
        get() = (binding as? ActivityContentListBinding)?.reloadButton

    private val adapter by lazy { ContentListAdapter(applicationContext) }
    private val recyclerView: RecyclerView?
        get() = (binding as? ActivityContentListBinding)?.recyclerView
    private lateinit var layoutManager: LinearLayoutManager

    private var contents: List<Content> by Delegates.observable(listOf()) { _, _, _ ->
        adapter.contents = contents
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recyclerView?.adapter = adapter
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager

        accountButton?.setOnClickListener {
            var selectedItem = 0
            val dialog = AlertDialog.Builder(this)
                .setTitle("アカウント")
                .setSingleChoiceItems(arrayOf("1: SharedPreferences", "2: Room"), selectedItem) { dlg, index ->
                    selectedItem = index
                    showToast("#issue selected item is $index")
                }
                .setPositiveButton("OK") { dlg, _ ->
                    dlg.dismiss()

                    when (selectedItem) {
                        0 -> {
                            // TODO
                            Log.d("setPositiveButton", "step2")
                            val userId = Me.shared.get(Me.Key.USER_ID) as String
                            val password  = Me.shared.get(Me.Key.PASSWORD) as String
                            Log.d("userId", userId.toString())
                            showToast("あなたのユーザーIDは${userId}、パスワードは${password}です")
                        }
                        1 -> {
                            // TODO
                            GlobalScope.launch {
                                Repository.localDb.accountDao().getAllContents().collect() {
                                    //TODO selectした結果の取得。
//                                    contents.forEach { account ->
//                                        showToast("あなたのユーザーIDは${userId}、パスワードは${password}です")
//                                    }
                                }
                            }
                        }
                    }
                }
                .setNegativeButton("閉じる", null)
                .create()
            dialog.show()
        }
        reloadButton?.setOnClickListener {
            // TODO
        }

        // TODO : showContentsFromCache
        // TODO : loadContentsFromServer
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
