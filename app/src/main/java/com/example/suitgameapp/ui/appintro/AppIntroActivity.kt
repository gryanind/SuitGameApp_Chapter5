package com.example.suitgameapp.ui.appintro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.suitgameapp.R
import com.example.suitgameapp.ui.appintro.inputname.InputNameFragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment

class AppIntroActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.txt_landing_page1).uppercase(),
                descriptionColorRes = R.color.white,
                backgroundColorRes = androidx.cardview.R.color.cardview_dark_background,
                imageDrawable = R.drawable.ic_landing_page1
        ))
        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.txt_landing_page2).uppercase(),
                descriptionColorRes = R.color.white,
                backgroundColorRes = androidx.cardview.R.color.cardview_dark_background,
                imageDrawable = R.drawable.ic_landing_page2
        ))
        addSlide(InputNameFragment())
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        if (currentFragment is InputNameFragment){
            currentFragment.navigateToMenuGame()
        }
    }
}