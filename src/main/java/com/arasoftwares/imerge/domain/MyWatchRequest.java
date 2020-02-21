package com.arasoftwares.imerge.domain;

public class MyWatchRequest{
    public String topicName;
    public String[] labelIds;

    public static MyWatchRequest createRequest(){
        MyWatchRequest wr=new MyWatchRequest();
        wr.topicName="projects/gpushproject/topics/gpushtopic";
        wr.labelIds=new String[1];
        wr.labelIds[0]="INBOX";
        return wr;
    }
}