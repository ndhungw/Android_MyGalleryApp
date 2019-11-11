package com.example.mygallery.AlbumsActivity;

import com.example.mygallery.DTOs.Album;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumBusinessLogic {
    public static int findSmallestMissingAlbumID(List<Album> allAlbums){
        int N = allAlbums.size();
        Set<Integer> set = new HashSet<>();
        for (Album a : allAlbums)
            set.add(a.getId());

        for (int i = 0; i < N ; i++) {
            if (!set.contains(i)) return i;
        }
        return N;
    }
}
