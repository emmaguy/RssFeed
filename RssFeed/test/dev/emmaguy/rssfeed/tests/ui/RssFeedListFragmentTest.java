package dev.emmaguy.rssfeed.tests.ui;

import static org.junit.Assert.assertNotNull;
import static org.robolectric.Robolectric.shadowOf;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import dev.emmaguy.rssfeed.R;
import dev.emmaguy.rssfeed.ui.RssFeedActivity;
import dev.emmaguy.rssfeed.ui.RssFeedListFragment;

@RunWith(org.robolectric.RobolectricTestRunner.class)
@Ignore("WIP")
public class RssFeedListFragmentTest {

    private RssFeedListFragment rssFeedListFragment;
    
    @Before
    public void setUp() throws Exception {

	rssFeedListFragment = new RssFeedListFragment();
	
	startFragment();
	
	assertNotNull(rssFeedListFragment);
	assertNotNull(rssFeedListFragment.getActivity());
    }
    
    public void startFragment()
    {
	FragmentActivity activity = new RssFeedActivity();
	shadowOf(activity).callOnCreate(null);
	
	FragmentManager fragmentManager = activity.getSupportFragmentManager();
	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	fragmentTransaction.add(rssFeedListFragment, null);
	fragmentTransaction.commit();
	
	shadowOf(activity).callOnStart();
        //shadowOf(activity).callOnResume();
    }
    
    @Test
    public void should_have_text_view_for_title() throws Exception {
	TextView titleView = (TextView) rssFeedListFragment.getListView().findViewById(R.id.title);
	
	assertNotNull(titleView);
    }
}
