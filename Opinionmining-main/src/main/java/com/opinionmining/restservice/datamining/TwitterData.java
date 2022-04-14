package com.opinionmining.restservice.datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.opinionmining.restservice.entity.FeedData;
import com.opinionmining.restservice.entity.Reply;
import com.opinionmining.restservice.service.FeedDataService;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterData {

	public static List<FeedData> twitterDataRetriever(String user, FeedDataService feedDataService) {
		List<FeedData> feeds = new ArrayList<>();
		Twitter twitter = new TwitterFactory().getInstance();

		twitter.setOAuthConsumer("d5b2EZ0Q5Ao88l5wNKpx0ruBV",
				"6TuspDnp7VnCcKiORHDaq4HCNWiR3Pw6RebsfiZfB4qTEqUQtN");
		twitter.setOAuthAccessToken(new AccessToken("2756073228-F1KOMJKYj0FcUCRS5sKE3iVv9xXQrCfyjl05zDp",
				"VvHXWz7dfKBrILpbu4xFce0KDekxrNauzc1cQLuPaVLCX"));

		try {

			ResponseList <Status> a = twitter.getUserTimeline(user,new Paging(1,100));
			Query query = new Query(user);
			query.setCount(100);
			QueryResult result = twitter.search(query);
			List<Status> tweets = new ArrayList<Status>();
			tweets.addAll(result.getTweets());
			//long lowestStatusId = Long.MAX_VALUE;
			System.out.println("Gathered "+tweets.size()+" tweets");
			for(Status b:a) {
				String txt = b.getText();
				boolean validtweet = true;
				if(txt.charAt(0)=='@' && !txt.contains("@"+user) || txt.startsWith("RT")) {
					validtweet = false;
				}
				Optional<FeedData> feedData = feedDataService.findById(String.valueOf(b.getId()));
				if(validtweet && !feedData.isPresent()) {

					FeedData feed = new FeedData();
					feed.setMessage(b.getText());
					feed.setId(String.valueOf(b.getId()));
					feed.setCreated_time(b.getCreatedAt());

					List<Reply> replies = new ArrayList<>();

					System.out.println(b.getText()+ " --- " +b.getFavoriteCount() +" --- "+b.getRetweetCount());
					System.out.println("______________________________________________________");

					System.out.println(b.getInReplyToStatusId()+"   "+b.getId());
					System.out.println("______________________________________________________");

					for(Status tweet: tweets) {

						if(b.getId()==tweet.getInReplyToStatusId()) {
							replies.add(new Reply(tweet.getCreatedAt(), tweet.getText(), String.valueOf(tweet.getId())));

							System.out.println(tweet.getText());
							System.out.println(tweet.getInReplyToStatusId()+"  "+tweet.getId());
							System.out.println("____________");
						}

					}
					feed.setReplies(replies);
					feeds.add(feed);
					System.out.println();
					System.out.println();
					System.out.println();
				}
			}


		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		return feeds;
	}
}
