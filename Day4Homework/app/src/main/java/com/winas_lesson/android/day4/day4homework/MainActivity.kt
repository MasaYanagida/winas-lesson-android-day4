package com.winas_lesson.android.day4.day4homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.day4homework.data.local.Me
import com.winas_lesson.android.day4.day4homework.data.model.Account
import com.winas_lesson.android.day4.day4homework.data.repository.Repository
import com.winas_lesson.android.day4.day4homework.databinding.ActivityMainBinding
import com.winas_lesson.android.day4.day4homework.interfaces.ViewBindable
import com.winas_lesson.android.day4.day4homework.ui.AbstractActivity
import com.winas_lesson.android.day4.day4homework.ui.ContentListActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            val userId = idTextView?.text ?: ""
            val password = passwordTextView?.text ?: ""

            // save to shared preferences
            Me.shared.put(Me.Key.USER_ID, userId.toString())
            Me.shared.put(Me.Key.PASSWORD, password.toString())

            GlobalScope.launch {
                // save to room
                Repository.localDb.accountDao().deleteAll()
                Repository.localDb.accountDao().add(Account(userId = userId.toString(), password = password.toString()))
                Handler(Looper.getMainLooper()).post {
                    val intent = Intent(applicationContext, ContentListActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}