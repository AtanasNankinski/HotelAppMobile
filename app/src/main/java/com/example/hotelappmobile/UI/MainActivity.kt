package com.example.hotelappmobile.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hotelappmobile.Models.FullUser
import com.example.hotelappmobile.Models.User
import com.example.hotelappmobile.R
import com.example.hotelappmobile.UI.Fragments.*
import com.example.hotelappmobile.ViewModels.MainViewModel
import com.example.hotelappmobile.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var loginIntent: Intent
    lateinit var user: FullUser
    lateinit var navView: NavigationView
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Declaring object and layout values
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        loginIntent = Intent(this, LoginActivity::class.java)
        drawerLayout = binding.drawerLayout
        navView = binding.NavigationView
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        // Calling methods and implementing views
        getUserCredentials(intent)
        setHeaderElements(user, navView)
        replaceFragment(HomeFragment(), getString(R.string.home))
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // Setting the Home fragment as first fragment
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideMenuItems(user, navView)

        navView.setNavigationItemSelectedListener {
            it.isChecked = true

            when(it.itemId){
                R.id.itemHome -> replaceFragment(HomeFragment(), getString(R.string.home))
                R.id.itemRegisterOwner -> replaceFragment(RegisterOwnerFragment(), getString(R.string.register_owner))
                R.id.itemRegisterManager -> replaceFragment(RegisterManagerFragment(), getString(R.string.register_manager))
                R.id.itemRegisterReceptionist -> replaceFragment(RegisterReceptionistFragment(), getString(R.string.register_receptionist))
                R.id.itemAddHotel -> replaceFragment(AddHotelFragment(), getString(R.string.add_hotel))
                R.id.itemLogout -> startActivity(loginIntent)
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Overriden method for onBackPressed with empty body in order to disable the back button action
    override fun onBackPressed() {}

    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    private fun getUserCredentials(intent: Intent){
        val id = intent.getIntExtra("id", 0)
        val userName = intent.getStringExtra("user_name")
        val email = intent.getStringExtra("email")
        val userType = intent.getIntExtra("user_type", 0)
        val token = intent.getStringExtra("token")

        user = FullUser(id, userName!!, email!!, userType, token!!)
    }

    private fun setHeaderElements(user: FullUser, navView: NavigationView){
        val header = navView.getHeaderView(0)
        val tvUsername = header.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = header.findViewById<TextView>(R.id.tvUserEmail)

        tvUsername.text = user.userName
        tvUserEmail.text = user.email
    }

    private fun hideMenuItems(user: FullUser, navView: NavigationView){
        when(user.userType){
            1 -> {
                navView.menu.findItem(R.id.itemRegisterManager).isVisible = false
                navView.menu.findItem(R.id.itemRegisterReceptionist).isVisible = false
            }
            2 -> {
                navView.menu.findItem(R.id.itemRegisterOwner).isVisible = false
                navView.menu.findItem(R.id.itemRegisterReceptionist).isVisible = false
                navView.menu.findItem(R.id.itemAddHotel).isVisible = false
            }
            3 -> {
                navView.menu.findItem(R.id.itemRegisterOwner).isVisible = false
                navView.menu.findItem(R.id.itemRegisterManager).isVisible = false
                navView.menu.findItem(R.id.itemAddHotel).isVisible = false
            }
            4 -> {
                navView.menu.findItem(R.id.itemRegisterOwner).isVisible = false
                navView.menu.findItem(R.id.itemRegisterManager).isVisible = false
                navView.menu.findItem(R.id.itemRegisterReceptionist).isVisible = false
                navView.menu.findItem(R.id.itemAddHotel).isVisible = false
            }
        }
    }
}