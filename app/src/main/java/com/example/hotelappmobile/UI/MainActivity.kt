package com.example.hotelappmobile.UI

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotelappmobile.R
import com.example.hotelappmobile.UI.Fragments.*
import com.example.hotelappmobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loginIntent = Intent(this, LoginActivity::class.java)

        drawerLayout = binding.drawerLayout
        val navView = binding.NavigationView

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // Setting the Home fragment as first fragment
        replaceFragment(HomeFragment(), getString(R.string.home))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }
}