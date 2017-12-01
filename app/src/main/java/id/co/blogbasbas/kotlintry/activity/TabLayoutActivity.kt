package id.co.blogbasbas.kotlintry

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import id.co.blogbasbas.kotlintry.fragment.Tab1Fragment
import id.co.blogbasbas.kotlintry.fragment.Tab2Fragment
import id.co.blogbasbas.kotlintry.fragment.Tab3Fragment
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        
        //title tab  >>>> tahap 1
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1").setIcon(R.drawable.ic_action_name))
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2").setIcon(android.R.drawable.ic_menu_delete))
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3").setIcon(android.R.drawable.ic_input_add))
        
        // tahap 2 tekan alt + enter untuk menciptakan tahap 3
        var adapter = TabAdapter(supportFragmentManager)

        // tahap 4
        pager.adapter = adapter

        //tahap 7 = agar bisa perpindah ketika di geser
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        //tahap 5 di implements ketika objek merah
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                //
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //tahap 6, cocokan pager degan tab, fungsi ketika di klik tab berpindah
                pager.setCurrentItem(tab?.position!!)
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        
        }
    }


        //tahap 3
        class TabAdapter(supportFragmentManager: FragmentManager?)
            : FragmentStatePagerAdapter (supportFragmentManager){

            override fun getItem(position: Int): Fragment {
                var fragment = Fragment()
                when(position){
                    0 -> fragment = Tab1Fragment()
                    1 -> fragment= Tab2Fragment()
                    2 -> fragment= Tab3Fragment()
                }

                return fragment

                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun getCount(): Int {
                return 3

                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
