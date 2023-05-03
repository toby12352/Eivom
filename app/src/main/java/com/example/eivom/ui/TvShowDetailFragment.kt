package com.example.eivom.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.eivom.R
import org.w3c.dom.Text

class TvShowDetailFragment: Fragment(R.layout.tv_details_activity) {

    private val args: TvShowDetailFragmentArgs by navArgs()

    private val favoriteViewModel: FavoriteTvShowViewModel by viewModels()
    private var isLiked = false

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        view.findViewById<TextView>(R.id.tvshowTitle).text = getString(R.string.tv_name, args.tvshowdetail.name)

        Glide.with(this)
            .load(args.tvshowdetail.poster_path)
            .into(view.findViewById(R.id.tvshowPoster))

        view.findViewById<TextView>(R.id.tvshowOverview).text = getString(R.string.tv_overview, args.tvshowdetail.overview)

        view.findViewById<TextView>(R.id.tvFirstAirDate).text = getString(R.string.tv_first_air_date, args.tvshowdetail.first_air_date)

        view.findViewById<TextView>(R.id.tvShowRating).text = getString(R.string.tv_rating, args.tvshowdetail.vote_average)

        Glide.with(this)
            .load(args.tvshowdetail.backdrop_path)
            .into(view.findViewById(R.id.tvBackdropImage))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_movie_detail, menu)

        val button = menu.findItem(R.id.action_favorite)
        favoriteViewModel.getTvShowByName(args.tvshowdetail.name).observe(viewLifecycleOwner) { movie ->
            when (movie) {
                null -> {
                    favoriteViewModel.removeFavoriteTvShow(args.tvshowdetail)
                    isLiked = false
                    button.isChecked = false
                    button.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_not_favorite_36
                    )
                }
                else -> {
                    favoriteViewModel.addFavoriteTvShow(args.tvshowdetail)
                    isLiked = true
                    button.isChecked = true
                    button.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite_36
                    )
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favorite -> {
                toggleMovieFavorite(item)
                true
            }
            R.id.action_share -> {
                shareContent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleMovieFavorite (menuItem: MenuItem) {
        when (isLiked) {
            false -> {
                favoriteViewModel.addFavoriteTvShow(args.tvshowdetail)
            }
            true -> favoriteViewModel.removeFavoriteTvShow(args.tvshowdetail)
        }
    }

    private fun shareContent() {
        val shareText = getString(R.string.share_text, args.tvshowdetail.name, args.tvshowdetail.poster_path)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(intent, null))
    }

}
