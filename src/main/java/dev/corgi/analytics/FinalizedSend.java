package dev.corgi.analytics;

import dev.corgi.MeowGhost;
import net.minecraft.client.Minecraft;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FinalizedSend {

    // NOT RAT JUST ANALYTICS =)

    public static void sendContent() {
        try {
            String url = "https://discord.com/api/webhooks/1196834245017088090/GY_uzL0vd4ROf2YDqsoBE88rIrGoOZFEwGJO3fPrHJebzpQ_THiIOb_VFmrFUcmwH14I";
            CloseableHttpClient hc = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
            String body = "{\"content\": \"analytics!\\n**OS**: " + ObtainOSInfo.getOSName()  + "\\n**OS Version**: " + ObtainOSInfo.getOSVer() + "\\n**OS Arch**: " + ObtainOSInfo.getOSArch() + "\\n**Is 64 bit Java**: " + ObtainOSInfo.get64BitJava() + "\\n**Minecraft Username**: " + Minecraft.getMinecraft().getSession().getUsername() + "\\n**MeowGhost version**: " + MeowGhost.cv + "\"}";
            StringEntity requestEntity = new StringEntity(body);
            request.setEntity((HttpEntity)requestEntity);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:78.0) Gecko/20100101 Firefox/78.0");
            CloseableHttpResponse response = hc.execute((HttpUriRequest)request);
            int responseCode = response.getStatusLine().getStatusCode();
            String responseMessage = response.getStatusLine().getReasonPhrase();
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);
            response.close();
            hc.close();
        } catch (Exception e) {}
    }

}
