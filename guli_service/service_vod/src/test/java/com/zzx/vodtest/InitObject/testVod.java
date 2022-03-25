package com.zzx.vodtest.InitObject;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public
class testVod {
    public static
    void main(String[] args) throws ClientException {
        String accessKeyId = "LTAI5t6Jqq8MGHrh6DVC4TjV";
        String accessKeySecret = "4s806pazgztUkSOypPsuRUUpXOMc7c";
        String title = "6 - What If I Want to Move Faster"; //上传之后文件的名称
        String fileName = "D:\\UsingTool\\$RVF682O.mp4"; //本地文件的路径和名称

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n"); //获取到上传视频的id
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //获取视频播放凭证
    public
    void TestGetAuth() throws ClientException {
        //创建初始化对象
        DefaultAcsClient cl = InitObject.initVodClient("LTAI5t6Jqq8MGHrh6DVC4TjV", "4s806pazgztUkSOypPsuRUUpXOMc7c");
        //创建获取视频地址request对象和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        //向request对象设置视频id值
        request.setVideoId("428cbfdcc0384fd3b7dc6b7d24d62ef6");

        GetVideoPlayAuthResponse response = cl.getAcsResponse(request);

        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

    //获取视频播放地址
    public
    void TestGetUrl() throws ClientException {
        //1、根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient cl = InitObject.initVodClient("LTAI5t6Jqq8MGHrh6DVC4TjV", "4s806pazgztUkSOypPsuRUUpXOMc7c");
        //创建获取视频地址request对象和response对象
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        //向request对象设置视频id值
        request.setVideoId("dea11efe9adf48a294a8a64c8250838d");


        //调用初始化对象里面的方法传递request，获取数据
        response = cl.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            //PlayInfo.PlayURL = https://outin-6f7d97b9a36c11ec901600163e1a65b6.oss-cn-shanghai.aliyuncs.com/sv/587e72ba-17f87701693/587e72ba-17f87701693.mp4?Expires=1647257048&OSSAccessKeyId=LTAI8bKSZ6dKjf44&Signature=BP224koq6QuHbB%2Bq8HBM50ZF3JY%3D
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");//VideoBase.Title = $RVF682O.mp4
    }
}
