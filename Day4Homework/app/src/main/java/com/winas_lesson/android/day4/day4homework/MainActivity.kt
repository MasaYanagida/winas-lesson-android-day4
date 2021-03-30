package com.winas_lesson.android.day4.day4homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import com.winas_lesson.android.day4.day4homework.data.local.Me
import com.winas_lesson.android.day4.day4homework.databinding.ActivityMainBinding
import com.winas_lesson.android.day4.day4homework.interfaces.ViewBindable
import com.winas_lesson.android.day4.day4homework.ui.AbstractActivity
import com.winas_lesson.android.day4.day4homework.ui.ContentListActivity

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
            val intent = Intent(applicationContext, ContentListActivity::class.java)
            startActivity(intent)
        }
    }
}