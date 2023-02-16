package com.mechanitis.demo.sense.client.user;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData {
    private static final int NUMBER_OF_LEADERS = 16;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();
    private final ObservableList<TwitterUser> items = observableArrayList();

    private int minCountForDisplay = 0;
    private String twitterHandle;

    public LeaderboardData() {
        this(NUMBER_OF_LEADERS);
    }

    LeaderboardData(int numberToDisplay) {
        IntStream.range(0, numberToDisplay)
                 .forEach(value -> items.add(new TwitterUser("")));
    }

    private int findPositionForUser(TwitterUser currentUser) {
        AtomicInteger positionForNewUser = new AtomicInteger(0);
        items.stream()
                .takeWhile(twitterUser -> twitterUser.getTweetCount() >=
                        currentUser.getTweetCount())
                .forEach(twitterUser -> positionForNewUser.incrementAndGet());
        return positionForNewUser.get();
    }

    private void putUserIntoNewPosition(TwitterUser currentUser, int itemToRemove) {
        items.remove(itemToRemove);

        int positionForNewUser = findPositionForUser(currentUser);
        items.add(positionForNewUser, currentUser);

        minCountForDisplay = items.get(items.size() - 1).getTweetCount();
    }

    private boolean userNeedsToMoveUpwards(TwitterUser currentUser, int currentIndex) {
        return currentIndex != 0 && items.get(currentIndex - 1).getTweetCount() < currentUser.getTweetCount();
    }

    private boolean userCanBeDisplayed(int numberOfTweets) {
        return numberOfTweets > minCountForDisplay;
    }

    ObservableList<TwitterUser> getItems() {
        return items;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(String item) {
        TwitterUser currentUser = allTwitterUsers.get(twitterHandle);
        if (currentUser == null){
            currentUser = new TwitterUser((twitterHandle));
        }
        currentUser.getTweetCount();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
