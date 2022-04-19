package com.mfe.eePipeServer.clientMgt;

import org.java_websocket.WebSocket;

import java.util.*;

public class ClientMap {
    static private ClientMap _clientMap=new ClientMap();
    final static HashMap<WebSocket, Integer> ConnClient;
    final static HashMap<Integer, WebSocket> ClientConn;
    final static HashMap<Integer, Set<Integer>> ClientSubscribers;

    static {
        ClientSubscribers = new HashMap<Integer, Set<Integer>>();
        ClientConn = new HashMap<Integer, WebSocket>();
        ConnClient = new HashMap<WebSocket, Integer>();
    }
    static public ClientMap getInstance(){
        return _clientMap;
    }
    private ClientMap() {
    }

    static private Integer _radomInt(int x, int y){
        return x + (int)(Math.random() * (y - x + 1));
    }
    static public Integer radomInt(){
        return _radomInt(0, 10000000);
    }

    public Integer getClientID(WebSocket conn){
        return ConnClient.get(conn);
    }
    public WebSocket getClientConn(Integer client){
        return ClientConn.get(client);
    }
    public Set<Integer> getClientSubscribersClients(Integer client){
        return ClientSubscribers.get(client);
    }
    public List<WebSocket> getConnSubscribersConns(WebSocket conn){
        Set<Integer> clients= ClientSubscribers.get(getClientID(conn));
        List<WebSocket> ret=null;
        if(null != clients){
            ret=new ArrayList<WebSocket>();
            Iterator<Integer> it=clients.iterator();
            while(it.hasNext()){
                ret.add(getClientConn(it.next()));
            }
        }
        return ret;
    }
    public void addClient(Integer client, WebSocket conn){
        ClientConn.put(client, conn);
        ConnClient.put(conn, client);
    }
    public void removeClient(Integer client, WebSocket conn){
        ClientConn.remove(client);
        ConnClient.remove(conn);
        ClientSubscribers.remove(client);
    }
    public void subscribe(Integer subscriber, Integer who){
        if(null!=ClientSubscribers.get(who)){
            ClientSubscribers.get(who).add(subscriber);
        }else{
            Set<Integer> n=new HashSet<Integer>();
            n.add(subscriber);
            ClientSubscribers.put(who, n);
        }
    }
}
