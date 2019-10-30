package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.ViewModel;

public class MainModel extends ViewModel {
    private int page = R.id.navigation_profile;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
