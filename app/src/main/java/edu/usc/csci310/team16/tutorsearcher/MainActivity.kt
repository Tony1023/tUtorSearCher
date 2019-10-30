package edu.usc.csci310.team16.tutorsearcher

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {
    lateinit var mainModel:MainModel
    lateinit var fragmentContainer: FragmentContainer
    lateinit var profile: ProfileFragment
    lateinit var search : SearchActivity
    lateinit var notification: NotificationActivity
    lateinit var rating: RatingActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = ProfileFragment()
        search = SearchActivity()
        notification = NotificationActivity()
        rating = RatingActivity()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)

        val menu = findViewById<BottomNavigationView>(R.id.navigation)
        menu.setOnNavigationItemSelectedListener { onClick(it) }

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container, profile)
        }.commit()

    }

    private fun onClick(v: MenuItem): Boolean {

        if(v.itemId == mainModel.page){
            return false
        } else {
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.fragment_container, when(v.itemId) {
                    R.id.navigation_search -> search
                    R.id.navigation_profile -> profile
                    R.id.navigation_notifications -> notification
                    R.id.navigation_tutors -> rating
                    else -> profile
                }).commit()
                mainModel.page = v.itemId
            }
            return true
        }
    }
}