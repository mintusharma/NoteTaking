package com.mintusharma.notetaking.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mintusharma.notetaking.data.Notes
import com.mintusharma.notetaking.data.Reminders

@BindingAdapter("notesList")
fun RecyclerView.submitList(notes: List<Notes>?) {
    val adapter = this.adapter as NotesListAdapter
    adapter.submitList(notes)
}

@BindingAdapter("remindersList")
fun RecyclerView.submitReminderList(reminders: List<Reminders>?) {
    val adapter = this.adapter as RemindersListAdapter
    adapter.submitList(reminders)
}
/*
@BindingAdapter("placeHolder")
fun ImageView.placeholderImage(drawable: Drawable?) {
    Glide.with(this.context).load(drawable).into(this)
}*/
/*
@BindingAdapter("displayIsFavorite")
fun ImageView.displayImage(isFavorite: Boolean?) {
    isFavorite?.let {
        if (it) {
            setImageDrawable(this.context.getDrawable(R.drawable.ic_favorite_active))
        } else {
            setImageDrawable(this.context.getDrawable(R.drawable.ic_favorite_inactive))
        }
    }
}

@BindingAdapter("resolveIntent")
fun FloatingActionButton.resolveIntent(intent: Intent) {
    when (intent.resolveActivity(this.context.packageManager)) {
        null -> {
            setImageResource(R.drawable.ic_no_photo)
            isEnabled = false
        }
        else -> {
            setImageResource(R.drawable.ic_camera)
            isEnabled = true
        }


    }
}*/
/*
@BindingAdapter("noteImage")
fun ImageView.setNoteImage(imageFile: File?) {
    Log.d("GLIDE", "setNoteImage: ${imageFile} ")
  imageFile?.let {
      if(it.exists()){
          visibility = View.VISIBLE
          Picasso.get()
              .load(imageFile)
              .placeholder(R.drawable.loading_animation)
              .memoryPolicy(MemoryPolicy.NO_CACHE)
              .into(this)
      }else{
          visibility = View.GONE
      }
  }*/
//}

