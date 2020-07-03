package com.mintusharma.notetaking.ui.fragments


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mintusharma.notetaking.ui.fragments.NotesEditFragmentArgs
import com.mintusharma.notetaking.R
import com.mintusharma.notetaking.databinding.FragmentNotesEditBinding
import com.mintusharma.notetaking.ui.dialogs.DatePickerFragment
import com.mintusharma.notetaking.ui.dialogs.DeleteDialogFragment
import com.mintusharma.notetaking.util.*
import com.mintusharma.notetaking.viewmodel.NotesEditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class NotesEditFragment : Fragment(), DatePickerFragment.Callbacks, DeleteDialogFragment.Callbacks{
    private val args by navArgs<NotesEditFragmentArgs>()
    private lateinit var binding: FragmentNotesEditBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var photoUri: Uri
    private val viewModel: NotesEditViewModel by viewModel {
        parametersOf(args.noteIdentifier)
    }

    companion object {
        private const val TAG = "NotesEditFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = FragmentNotesEditBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setuptoolbar()
        setHasOptionsMenu(true)
        return binding.root
    }

    fun setuptoolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.editToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            appBarConfig = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfig)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_optionmenu, menu)
        menu.findItem(R.id.edit_item_delete).isVisible = args.noteIdentifier != null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*viewModel.snackBarEvent.observe(viewLifecycleOwner, EventObserver { message ->
            if (!TextUtils.isEmpty(message)) {
              //  showSnackBar(message, null)
            }
        })*/
        viewModel.navigationEvent.observe(viewLifecycleOwner, EventObserver { destination ->
            when (destination) {
                Destination.UP -> {
                    navController.navigateUp()
                }
                Destination.VIEWPAGERFRAGMENT -> {
                    navController.popBackStack(R.id.homeViewPagerFragment, false)
                }
            }
        })

        viewModel.showDatePickerEvent.observe(viewLifecycleOwner, EventObserver { date ->
            DatePickerFragment.newInstance(date).apply {
                setTargetFragment(this@NotesEditFragment, REQUEST_DATE)
                show(this@NotesEditFragment.requireFragmentManager(), DIALOG_DATE)
            }
        })
        viewModel.showDeleteDialogEvent.observe(viewLifecycleOwner, EventObserver {message->
            DeleteDialogFragment.newInstance(message).apply {
                setTargetFragment(this@NotesEditFragment, REQUEST_DELETE_ANSWER)
                show(this@NotesEditFragment.requireFragmentManager(), DIALOG_DELETE)
            }
        })
    }

   /* fun showSnackBar(message: String, actionMessage: String?) {
        val snackbar = Snackbar.make(
            binding.editCoordinatorlayout,
            message,
            Snackbar.LENGTH_LONG
        )
        actionMessage?.let {
            snackbar.setAction(actionMessage) {
              //  openAppSetting()
            }
        }
        snackbar.show()
    }*/


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_item_save -> {
                viewModel.saveNote()
                true
            }

            R.id.edit_item_delete -> {
                viewModel.showDeleteDialog()
                true
            }
            android.R.id.home -> {
                viewModel.navigateUp()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDateSelected(date: String) {
        viewModel.updateDateTextView(date)
    }

    override fun onPositiveButtonClick() {
        viewModel.deleteAndNavigateToList()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            resultCode!= Activity.RESULT_OK->return
            requestCode == REQUEST_PHOTO->{
                requireActivity().revokeUriPermission(photoUri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            }
        }

    }


}
