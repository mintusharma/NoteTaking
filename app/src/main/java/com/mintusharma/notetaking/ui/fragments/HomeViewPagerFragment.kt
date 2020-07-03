package com.mintusharma.notetaking.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mintusharma.notetaking.ui.fragments.HomeViewPagerFragmentDirections

import com.mintusharma.notetaking.R
import com.mintusharma.notetaking.adapters.DailyNotePagerAdapter
import com.mintusharma.notetaking.adapters.NOTES_LIST_PAGE_INDEX
import com.mintusharma.notetaking.adapters.REMINDERS_LIST_PAGE_INDEX
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.data.Reminders
import com.mintusharma.notetaking.databinding.FragmentHomeViewPagerBinding
import com.mintusharma.notetaking.util.EventObserver
import com.mintusharma.notetaking.viewmodel.HomeViewPagerViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IndexOutOfBoundsException

/**
 * A simple [Fragment] subclass.
 */
class HomeViewPagerFragment : Fragment(),NotesListFragment.Callbacks,RemindersListFragment.Callbacks {
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager:ViewPager2
    private val viewPagerViewModel: HomeViewPagerViewModel by viewModel()
    private lateinit var navController:NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController =findNavController()
        val binding = FragmentHomeViewPagerBinding.inflate(layoutInflater,container,false)
        binding.viewModel = viewPagerViewModel
        tabLayout = binding.tabs
        viewPager = binding.viewPager
        viewPager.adapter = DailyNotePagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFabNavigation()
    }

    fun setupFabNavigation(){
        viewPagerViewModel.fabNavListenner.observe(viewLifecycleOwner,EventObserver{
            when(viewPager.currentItem){
                NOTES_LIST_PAGE_INDEX-> {
                    val action =
                        HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToNotesEditFragment(
                            null
                        )
                    findNavController().navigate(action)
                }
                REMINDERS_LIST_PAGE_INDEX->{
                   val action =
                       HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToReminderEditFragment(
                           null
                       )
                    findNavController().navigate(action)
                }

            }

        })
    }

    private fun getTabIcon(position:Int):Int=
         when(position){
            NOTES_LIST_PAGE_INDEX -> R.drawable.ic_note_list_selector
            REMINDERS_LIST_PAGE_INDEX->R.drawable.ic_reminder_list_selector
            else->throw IndexOutOfBoundsException()
        }

    private fun getTabTitle(position: Int):String? =
        when(position){
            NOTES_LIST_PAGE_INDEX->getString(R.string.my_notes_list_title)
            REMINDERS_LIST_PAGE_INDEX->getString(R.string.my_reminders_list_title)
            else->null
        }

    override fun onNoteClick(note: Notes) {
        val action =
            HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToNotesDisplayFragment(
                note.noteIdentifier
            )
        navController.navigate(action)
    }

    override fun onReminderClick(reminder: Reminders) {
        val action =
            HomeViewPagerFragmentDirections.actionHomeViewPagerFragmentToReminderEditFragment(
                reminder.reminderIndentifier
            )
        navController.navigate(action)
    }


}
