package com.khmelenko.lab.travisclient.task.travis;

import android.text.TextUtils;

import com.khmelenko.lab.travisclient.event.travis.FindReposEvent;
import com.khmelenko.lab.travisclient.event.travis.LoadingFailedEvent;
import com.khmelenko.lab.travisclient.network.response.Repo;
import com.khmelenko.lab.travisclient.task.Task;
import com.khmelenko.lab.travisclient.task.TaskError;
import com.khmelenko.lab.travisclient.task.TaskException;

import java.util.List;

/**
 * Task for finding repositories
 *
 * @author Dmytro Khmelenko
 */
public class FindRepoTask extends Task<List<Repo>> {

    private String mSearch;

    public FindRepoTask() {
    }

    public FindRepoTask(String search) {
        mSearch = search;
    }

    @Override
    public List<Repo> execute() throws TaskException {
        List<Repo> repos;
        if (!TextUtils.isEmpty(mSearch)) {
            repos = travisClient().getApiService().getRepos(mSearch);
        } else {
            repos = travisClient().getApiService().getRepos();
        }
        return repos;
    }

    @Override
    public void onSuccess(List<Repo> result) {
        FindReposEvent event = new FindReposEvent(result);
        eventBus().post(event);
    }

    @Override
    public void onFail(TaskError error) {
        LoadingFailedEvent event = new LoadingFailedEvent(error);
        eventBus().post(event);
    }
}
