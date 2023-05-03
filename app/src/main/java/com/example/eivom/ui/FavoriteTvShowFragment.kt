package com.example.eivom.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eivom.R
import com.example.eivom.data.MovieList
import com.example.eivom.data.TvShowList

class FavoriteTvShowFragment: Fragment(R.layout.favorite_movie_fragment) {
    private val args: MovieDetailFragmentArgs by navArgs()

    private val favoriteViewModel: FavoriteTvShowViewModel by viewModels()
    private val favoriteMovieAdapter = TvShowInfoAdapter(::onFavoriteMovieClick)
    private lateinit var favoriteMovieRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         * Set up RecyclerView
         */
        favoriteMovieRV = view.findViewById(R.id.rv_favorite_movies)
//        favoriteMovieRV.layoutManager = LinearLayoutManager(requireContext())
        favoriteMovieRV.layoutManager = GridLayoutManager(requireContext(), 2)
        favoriteMovieRV.setHasFixedSize(true)
        favoriteMovieRV.adapter = favoriteMovieAdapter

        favoriteMovieRV.addItemDecoration(FavoriteMoviesFragment.ItemSpacingDecoration(16))

        favoriteViewModel.favoriteTvShows.observe(viewLifecycleOwner) {
            favoriteMovieAdapter.updateInfo(it)
        }
    }

    private fun onFavoriteMovieClick(movie: TvShowList) {
        val directions = FavoriteTvShowFragmentDirections.navigateToTvshowDetail(movie)
        findNavController().navigate(directions)
    }

    class ItemSpacingDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            // Set spacing for all items except the first
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.top = space
            }
        }
    }
}