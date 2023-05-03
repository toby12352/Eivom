package com.example.eivom.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.eivom.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieDetailFragment : Fragment(R.layout.movie_details_activity) {
    private val args: MovieDetailFragmentArgs by navArgs()

    private val favoriteViewModel: FavoriteMoviesViewModel by viewModels()
    private var isLiked = false

    private var key = "NsUWXo8M7UA"
    private var type = ""
    private var official = false

    private val videoInfoViewModel : VideoInfoViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        view.findViewById<TextView>(R.id.personTitle).text = getString(R.string.movie_title, args.moviedetail.title)

        Glide.with(this)
            .load(args.moviedetail.poster_path)
            .into(view.findViewById(R.id.personPoster))

        view.findViewById<TextView>(R.id.personAppearsIn).text = getString(R.string.movie_description, args.moviedetail.overview)

        view.findViewById<TextView>(R.id.movieReleaseDate).text = getString(R.string.movie_release_date, args.moviedetail.release_date)

        view.findViewById<TextView>(R.id.movieRating).text = getString(R.string.movie_rating, args.moviedetail.vote_average)

        val testTrailer = view.findViewById<YouTubePlayerView>(R.id.movieTrailer)

        testTrailer.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(key, 0f)
            }
        }, IFramePlayerOptions.default)

        videoInfoViewModel.info.observe(viewLifecycleOwner) { info ->
            if (info != null) {
                for (result in info.results) {
                    if (result.type == "Trailer" && result.official)
                        key = result.key
                }
            }
        }

        videoInfoViewModel.loadVideoInfo(args.moviedetail.id, "1f89bc62d244a63f91c60d7a7381ebd3")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_movie_detail, menu)

        val button = menu.findItem(R.id.action_favorite)
        favoriteViewModel.getMovieByName(args.moviedetail.title).observe(viewLifecycleOwner) { movie ->
            when (movie) {
                null -> {
                    isLiked = false
                    button.isChecked = false
                    button.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_not_favorite_36
                    )
                }
                else -> {
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
                favoriteViewModel.addFavoriteMovie(args.moviedetail)
            }
            true -> favoriteViewModel.removeFavoriteMovie(args.moviedetail)
        }
    }

    private fun shareContent() {
        val shareText = getString(R.string.share_text, args.moviedetail.title, args.moviedetail.poster_path)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(intent, null))
    }
}