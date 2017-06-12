package com.example.android.cinemusp;

import java.util.ArrayList;

/**
 * Created by bruno on 6/10/17.
 */

public class MovieList {


    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    private ArrayList<Movie> movies;


MovieList() {
    movies = new ArrayList<Movie>();
    movies.add(new Movie("Wonder Woman", "Before she was Wonder Woman she was Diana, princess of the Amazons, trained warrior. When a pilot crashes and tells of conflict in the outside world, she leaves home to fight a war to end all wars, discovering her full powers and true destiny.",
            "http://cdn2-www.comingsoon.net/assets/uploads/gallery/wonder-woman/wwposter5.jpg", "8.2/10", "141min", "2 June 2017", "Patty Jenkins", "Gal Gadot", "http://www.imdb.com/video/imdb/vi4045256473/imdb/embed?autoplay=false&width=480", "Action"));

    movies.add(new Movie("Pirates of the Caribbean: Dead Men Tell No Tales", "Captain Jack Sparrow searches for the trident of Poseidon while being pursued by an undead sea captain and his crew.",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYyMTcxNzc5M15BMl5BanBnXkFtZTgwOTg2ODE2MTI@._V1_SY1000_CR0,0,674,1000_AL_.jpg",
            "7.1/10", "129min", "26 May 2017", "Joachim Ronning & Espen Sandberg", "Johnny Depp", "http://www.imdb.com/video/imdb/vi172733977/imdb/embed?autoplay=false&width=480", "Adventure"));

    movies.add(new Movie("The Mummy", "An ancient princess is awakened from her crypt beneath the desert, bringing with her malevolence grown over millennia, and terrors that defy human comprehension.",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMjM5NzM5NTgxN15BMl5BanBnXkFtZTgwNDEyNTk4MTI@._V1_SY1000_CR0,0,631,1000_AL_.jpg",
            "6.0/10", "110min", "9 June 2017", "Alex Kurtzman", " Tom Cruise", "\"http://www.imdb.com/video/imdb/vi981711129/imdb/embed?autoplay=false&width=480", "Adventure"));

    movies.add(new Movie("It Comes at Night", "Secure within a desolate home as an unnatural threat terrorizes the world, a man has established a tenuous domestic order with his wife and son, but this will soon be put to test when a desperate young family arrives seeking refuge.",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BMjQ3MDA0ODA2N15BMl5BanBnXkFtZTgwNzg0NzgwMjI@._V1_SY1000_CR0,0,676,1000_AL_.jpg",
            "7.3/10", "91min", "9 June 2017", "Trey Edward Shults", "Joel Edgerton", "http://www.imdb.com/video/imdb/vi2129967385/imdb/embed?autoplay=false&width=480", "Terror"));

    movies.add(new Movie("Baywatch", "Devoted lifeguard Mitch Buchannon butts heads with a brash new recruit, as they uncover a criminal plot that threatens the future of the bay.",
            "https://images-na.ssl-images-amazon.com/images/M/MV5BNTA4MjQ0ODQzNF5BMl5BanBnXkFtZTgwNzA5NjYzMjI@._V1_SY1000_CR0,0,674,1000_AL_.jpg",
            "5.7/10", "117min", "25 May 2017", "Seth Gordon", " Dwayne Johnson & Zac Efron", "http://www.imdb.com/video/imdb/vi1469888793/imdb/embed?autoplay=false&width=480", "Comedy"));

}


}
