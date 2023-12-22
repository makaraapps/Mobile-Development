package com.makara

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.makara.databinding.ActivityMainBinding
import com.makara.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var token = ""

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupUser()
        setupView()
        setupAction()
    }

    private fun setupUser() {
        viewModel.getSession().observe(this) {user ->
            token = user.token
            if (!user.isLogin) {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()

        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.cameraFragment -> binding.clBottomNav.visibility = View.GONE
                R.id.uploadImageFragment -> binding.clBottomNav.visibility = View.GONE
                else -> binding.clBottomNav.visibility = View.VISIBLE
            }
        }

        binding.navBtnCamera.setOnClickListener {
            if(navController?.currentDestination?.id != R.id.cameraFragment){
                navController?.navigate(R.id.cameraFragment)
            }
        }

        binding.navBtnHome.setOnClickListener {
            if(navController?.currentDestination?.id != R.id.homeFragment){
                navController?.navigate(R.id.homeFragment)
            }
        }
    }

    private fun setupAction() {
        binding.navBtnLogout.setOnClickListener {
            val title = TextView(this)
            title.text = getString(R.string.logout)
            title.setTextColor(ContextCompat.getColor(this, R.color.brown))
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            title.setTypeface(null, Typeface.BOLD)
            title.setPadding(40, 40, 40, 20)

            AlertDialog.Builder(this).apply {
                setCustomTitle(title)
                setMessage("Are you sure you want to log out?")
                setNegativeButton("No") { _, _ ->
                }
                setPositiveButton("Yes") { _, _ ->
                    viewModel.logout()
                    finish()
                }
                create()
                show()
            }
        }
    }
}