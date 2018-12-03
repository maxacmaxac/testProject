package com.example.marjancvetkovic.corutinesexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.marjancvetkovic.corutinesexample.view.fragments.BmfListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).appComponent.inject(this)

        val fragmentTag = BmfListFragment::class.java.simpleName
        var bmfListFragment = BmfListFragment.getInstance()
        val commitFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        bmfListFragment = commitFragment as BmfListFragment? ?: bmfListFragment
        supportFragmentManager.beginTransaction().replace(R.id.mainContent, bmfListFragment, fragmentTag).commit()
    }
}
