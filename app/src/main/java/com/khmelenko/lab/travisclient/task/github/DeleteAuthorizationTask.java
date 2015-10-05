package com.khmelenko.lab.travisclient.task.github;

import android.text.TextUtils;

import com.khmelenko.lab.travisclient.event.github.DeleteAuthorizationSuccessEvent;
import com.khmelenko.lab.travisclient.event.github.GithubAuthorizationFailEvent;
import com.khmelenko.lab.travisclient.network.retrofit.GithubApiService;
import com.khmelenko.lab.travisclient.task.Task;
import com.khmelenko.lab.travisclient.task.TaskError;
import com.khmelenko.lab.travisclient.task.TaskException;

import retrofit.client.Header;
import retrofit.client.Response;

/**
 * The task for deletion authorization
 *
 * @author Dmytro Khmelenko
 */
public final class DeleteAuthorizationTask extends Task<Void> {

    private final String mBasicAuth;
    private final String mAuthorizationId;
    private String mTwoFactorCode;

    public DeleteAuthorizationTask(String basicAuth, String authorizationId) {
        mBasicAuth = basicAuth;
        mAuthorizationId = authorizationId;
    }

    public DeleteAuthorizationTask(String basicAuth, String authorizationId, String twoFactorCode) {
        mBasicAuth = basicAuth;
        mAuthorizationId = authorizationId;
        mTwoFactorCode = twoFactorCode;
    }

    @Override
    public Void execute() throws TaskException {


        try {
            if (!TextUtils.isEmpty(mTwoFactorCode)) {
                mRestClient.getGithubApiService().deleteAuthorization(mBasicAuth, mTwoFactorCode,
                        mAuthorizationId);
            } else {
                mRestClient.getGithubApiService().deleteAuthorization(mBasicAuth, mAuthorizationId);
            }
        } catch (TaskException error) {

            Response response = error.getTaskError().getResponse();
            boolean twoFactorAuthRequired = false;
            for (Header header : response.getHeaders()) {
                if (GithubApiService.TWO_FACTOR_HEADER.equals(header.getName())) {
                    twoFactorAuthRequired = true;
                    break;
                }
            }
            if (response.getStatus() == 401 && twoFactorAuthRequired) {
                TaskError taskError = new TaskError(TaskError.TWO_FACTOR_AUTH_REQUIRED, "");
                throw new TaskException(taskError);
            } else {
                throw error;
            }
        }

        return null;
    }

    @Override
    public void onSuccess(Void result) {
        DeleteAuthorizationSuccessEvent event = new DeleteAuthorizationSuccessEvent();
        mEventBus.post(event);
    }

    @Override
    public void onFail(TaskError error) {
        GithubAuthorizationFailEvent event = new GithubAuthorizationFailEvent(error);
        mEventBus.post(event);
    }
}
