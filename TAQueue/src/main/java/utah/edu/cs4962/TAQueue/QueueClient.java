package utah.edu.cs4962.TAQueue;

import android.util.Base64;

import org.apache.http.Header;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.json.JSONArray;
import com.loopj.android.http.*;

/**
 * Created by shong on 12/4/13.
 */
public class QueueClient
{
    private static final String BASE_URL = "http://nine.eng.utah.edu/";
    private static JSONArray _response = null;

    private AsyncHttpClient client = new AsyncHttpClient();

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {

        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public void delete(String url, AsyncHttpResponseHandler responseHandler)
    {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    public void authGet(String id, String token, String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, token);
        Header header = BasicScheme.authenticate(credentials, "UTF-8", false);
        Header[] headers = {header};
        client.removeHeader("Authorization");
        client.get(null, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public void authDelete(String id, String token, String url, AsyncHttpResponseHandler responseHandler)
    {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, token);
        Header header = BasicScheme.authenticate(credentials, "UTF-8", false);
        Header[] headers = {header};
        client.removeHeader("Authorization");
        client.delete(null, getAbsoluteUrl(url), headers, responseHandler);
    }

    public void setBasicAuth(String username, String password)
    {
        client.setBasicAuth(username, password);
    }

    public void addAuthHeader(String id, String token)
    {
        String basicAuth = id + ":" + token;
        basicAuth = Base64.encodeToString(basicAuth.getBytes(), Base64.NO_WRAP);
        client.addHeader("Authorization", "Basic " + basicAuth);
    }

    public void removeAuthHeader()
    {
        client.removeHeader("Authorization");
    }


    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public void setTimeout(int timeout)
    {
        client.setTimeout(timeout);
    }

}
