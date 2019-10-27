package edu.usc.csci310.team16.tutorsearcher

import android.app.ActivityGroup
import android.content.Intent
import android.os.Bundle
import android.widget.TabHost

class TabsActivity : ActivityGroup() {

    private lateinit var tabHost: TabHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabHost = findViewById(R.id.tabHost)
        tabHost.setup()
        tabHost.setup(localActivityManager)

        var spec = tabHost.newTabSpec("Search Tab")
        spec.setIndicator("Search")
        var intent = Intent(this, SearchActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("Tutors Tab")
        spec.setIndicator("Tutors")
        intent = Intent(this, RatingActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("Notification Tab")
        spec.setIndicator("Notification")
        intent = Intent(this, NotificationActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("Profile Tab")
        spec.setIndicator("Profile")
        intent = Intent(this, ProfileActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)
    }




}