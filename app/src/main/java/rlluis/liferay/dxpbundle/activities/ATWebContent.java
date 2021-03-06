package rlluis.liferay.dxpbundle.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liferay.mobile.android.callback.typed.JSONArrayCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rlluis.liferay.dxpbundle.mobileSDK.GetUserSegment;
import rlluis.liferay.dxpbundle.R;

public class ATWebContent extends AppCompatActivity {

    WebContentDisplayScreenlet webcontent;
    String articulo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atweb_content);

        //cálculo de segmentos a los que pertenece el usuario, si pertenece al segmento XX, entonces webcontent XXX, si no, webcontent YYY

        Session session = SessionContext.createSessionFromCurrentSession();
        User user = SessionContext.getCurrentUser();
        GetUserSegment service = new GetUserSegment(session);
        boolean userSegmentActive=true;
        session.setCallback(new JSONArrayCallback() {
            @Override
            public void onFailure(Exception exception) {
                LiferayLogger.e(exception.toString());
            }

            @Override
            public void onSuccess(JSONArray result) {
                String userSegmentId=getString(R.string.user_segment);
                Boolean resultado=false;
                try {

                    if (result != null) {
                        String segmento="";
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject jsonObject = result.getJSONObject(i);
                            segmento= jsonObject.getString("userSegmentId").toString();
                            //segmento= result.getString("userSegmentId").toString();
                            if (segmento.equals(userSegmentId)){
                                resultado=true;
                            }
                        }
                    }

                    if (resultado){
                        articulo=getString(R.string.webcontent_usersegment);
                    }else{
                        articulo=getString(R.string.webcontent_normal);
                    }

                    //boolean enSegmento = service.userInSegment(user.getId(), getString(R.string.user_segment),userSegmentActive);
                    webcontent = (WebContentDisplayScreenlet) findViewById(R.id.web_atarticle);
                    webcontent.setArticleId(articulo);
                    webcontent.load();
                } catch (Exception e){
                    e.printStackTrace();
                }}
        });

        try {
            service.getUserSegments(user.getId(), userSegmentActive);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }


    }


}
