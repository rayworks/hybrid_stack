package com.rayworks.hybridstack

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Show you around ?", Snackbar.LENGTH_LONG)
                .setAction("Yes") {
                    val ctx = this@MainActivity
                    val app = application as App
                    app.droidPlugin.normalMethodHandler.invokeMethod(
                        "gotoRoute", nextInt(1000) % 2,
                        callback = {}
                    )
                    val intent = MyFlutterActivity.EngineIntentBuilder(
                        MyFlutterActivity::class.java,
                        "flutter_engine_id"
                    ).build(ctx)

                    startActivity(intent)
                    overridePendingTransition(0, 0)
                }.show()
        }

        nav_text.text = this.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
