package com.ss.moviedb_kotlin.util

class Const {
    companion object {
        // * MovieDB API
        const val API_KEY = "20741d03c7361873c93ebfeaa4819e40"
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val MOVIEDB_STARTING_PAGE_INDEX = 1;
        const val MOVIEDB_PAGE_SIZE = 30;

        // List of image https://www.themoviedb.org/talk/5ff32c1467203d003fcb7a21
        const val IMG_URL_185 = "https://image.tmdb.org/t/p/w185/"
        const val IMG_URL_500 = "https://image.tmdb.org/t/p/w500/"
        const val IMG_URL_780 = "https://image.tmdb.org/t/p/w780/"

        // * YouTube
        const val YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v="

        // * Room Database
        const val MOVIEDB_DATABASE = "moviedb"
        const val NOW_PLAYING_MOVIES_TABLE = "now_playing_movies"
        const val NOW_PLAYING_REMOTE_KEYS_TABLE = "now_playing_remote_keys"
    }
}