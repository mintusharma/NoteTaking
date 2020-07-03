package com.mintusharma.notetaking.util

import android.content.Intent
import android.provider.MediaStore

val captureImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

enum class Destination {
    EDITFRAGMENT,
    VIEWPAGERFRAGMENT,
    UP
}

enum class Filter {
    ALL,
    FAVORITES
}

