package com.ss.moviedb_kotlin.util

class Const {
    companion object {
        // * MovieDB API
        const val API_KEY = "20741d03c7361873c93ebfeaa4819e40"
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val MOVIEDB_STARTING_PAGE_INDEX = 1;
        const val MOVIEDB_PAGE_SIZE = 9;

        // List of image https://www.themoviedb.org/talk/5ff32c1467203d003fcb7a21
        const val IMG_URL_185 = "https://image.tmdb.org/t/p/w185/"
        const val IMG_URL_500 = "https://image.tmdb.org/t/p/w500/"
        const val IMG_URL_780 = "https://image.tmdb.org/t/p/w780/"

        // * YouTube
        const val YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v="

        // * Room Database
        const val MOVIEDB_DATABASE = "moviedb"
        const val POPULAR_MOVIES_TABLE = "popular_movies"
        const val POPULAR_REMOTE_KEYS_TABLE = "popular_remote_keys"
        const val TOP_RATED_MOVIES_TABLE = "top_rated_movies"
        const val TOP_RATED_REMOTE_KEYS_TABLE = "top_rated_remote_keys"
        const val NOW_PLAYING_MOVIES_TABLE = "now_playing_movies"
        const val NOW_PLAYING_REMOTE_KEYS_TABLE = "now_playing_remote_keys"
        const val UPCOMING_MOVIES_TABLE = "upcoming_movies"
        const val UPCOMING_REMOTE_KEYS_TABLE = "upcoming_remote_keys"
    }
}