package com.winas_lesson.android.day4.day4homework

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.day4homework.data.local.Me
import com.winas_lesson.android.day4.day4homework.data.model.Account
import com.winas_lesson.android.day4.day4homework.data.model.Content
import com.winas_lesson.android.day4.day4homework.data.repository.Repository
import com.winas_lesson.android.day4.day4homework.databinding.ActivityMainBinding
import com.winas_lesson.android.day4.day4homework.interfaces.ViewBindable
import com.winas_lesson.android.day4.day4homework.ui.AbstractActivity
import com.winas_lesson.android.day4.day4homework.ui.ContentListActivity
import com.winas_lesson.android.day4.day4homework.util.jsonToList
import com.winas_lesson.android.day4.day4homework.util.readStringFromAssetFile
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
            val id: String = idTextView?.text.toString()
            val password: String = passwordTextView?.text.toString()
            val account = Account(id, password)
            Me.shared.put(Me.Key.USER_ID, id)
            Me.shared.put(Me.Key.PASSWORD, password)
            GlobalScope.launch {
                Repository.localDb.accountDao().deleteAll()
                Repository.localDb.accountDao().addAccount(account)
            }

            val intent = Intent(applicationContext, ContentListActivity::class.java)
            startActivity(intent)
        }

        val firstLaunch: Boolean = (Me.shared.get(Me.Key.FIRST_LAUNCH) as? Boolean) ?: true
        if (firstLaunch) {
            Me.shared.put(Me.Key.FIRST_LAUNCH, false)
            loadContentsFromMaster {
                Timber.d("Data successfully loaded from master!")
            }
        }
    }

    private fun loadContentsFromMaster(completion: (() -> Unit)) {
        GlobalScope.launch {
            val jsonString: String = readStringFromAssetFile("sample-list.json")
            val contents = jsonString.jsonToList<Content>()
            GlobalScope.launch {
                if (contents != null) {
                    Repository.localDb.contentDao().deleteAll()
                    Repository.localDb.contentDao().addAllContents(contents)
                }
            }
            Handler(Looper.getMainLooper()).post {
                completion()
            }
        }
    }
}