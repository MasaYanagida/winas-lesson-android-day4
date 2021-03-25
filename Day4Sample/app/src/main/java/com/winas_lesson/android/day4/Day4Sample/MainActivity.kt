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
