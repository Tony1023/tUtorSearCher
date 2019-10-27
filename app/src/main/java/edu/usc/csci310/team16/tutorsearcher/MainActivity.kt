package edu.usc.csci310.team16.tutorsearcher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {
    lateinit var mainModel:MainModel
    lateinit var fragmentContainer: FragmentContainer
    lateinit var profile: ProfileActivity
    lateinit var search : SearchActivity
    lateinit var notification: NotificationActivity
    lateinit var rating: RatingActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = ProfileActivity()
        search = SearchActivity()
        notification = NotificationActivity()
        rating = RatingActivity()

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        mainModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainModel::class.java)
        supportFragmentManager.commit {
            replace(R.id.fragment_container,ProfileActivity())
        }

    }

    fun click(v: View){
        if(v.id == mainModel.page){
            return
        }else{
            supportFragmentManager
            TODO("Route switching")
            return
        }
    }
}