package com.winas_lesson.android.day4.day4homework

import android.content.Intent
import android.gesture.GesturePoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.day4homework.data.local.Me
import com.winas_lesson.android.day4.day4homework.databinding.ActivityMainBinding
import com.winas_lesson.android.day4.day4homework.data.repository.Repository
import com.winas_lesson.android.day4.day4homework.util.readStringFromAssetFile
import com.winas_lesson.android.day4.day4homework.util.jsonToList
import com.winas_lesson.android.day4.day4homework.data.model.Content
import com.winas_lesson.android.day4.day4homework.data.model.Account



import com.winas_lesson.android.day4.day4homework.interfaces.ViewBindable
import com.winas_lesson.android.day4.day4homework.ui.AbstractActivity
import com.winas_lesson.android.day4.day4homework.ui.ContentListActivity
import kotlinx.android.synthetic.main.activity_content_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AbstractActivity(), ViewBindable {
    override lateinit var binding: ViewBinding
    private val idTextView: EditText?
        get() = (binding as? ActivityMainBinding)?.idTextView
    private val passwordTextView: EditText?
        get() = (binding as? ActivityMainBinding)?.passwordTextView
    private val button: Button?
        get() = (binding as? ActivityMainBinding)?.button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        button?.setOnClickListener {
            // TODO : save account data to (1)SharedPreference(Me), (2)Room(Account)
            Me.shared.put(Me.Key.USER_ID , this.idTextView?.text.toString())
            Me.shared.put(Me.Key.PASSWORD, this.passwordTextView?.text.toString())
            this.setAccountRoom {  }
            val intent = Intent(applicationContext, ContentListActivity::class.java)
            startActivity(intent)


        }
    }


    private fun setAccountRoom(completion: (() -> Unit)) {
        GlobalScope.launch {


            Repository.localDb.accountDao().deleteAll()
            val newAccount = com.winas_lesson.android.day4.day4homework.data.model.Account(0,"aaaa", "bbbb")
            // save in local database as cache
            Repository.localDb.accountDao().add(newAccount)
            Handler(Looper.getMainLooper()).post {
                completion()
            }
        }
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
}