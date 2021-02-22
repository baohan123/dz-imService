package com.dz.dzim.service.impl;

import com.dz.dzim.service.Meeting;
import com.dz.dzim.service.MeetingActor;
import com.dz.dzim.service.impl.MeetingActorImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 会场的基础类
 * @author qianyangdong
 */
@Service
public class MeetingBase implements Meeting
{
	private final String mId = UUID.randomUUID().toString(); //会场唯一编号
	
	private final Map<String, MeetingActorImpl> mActors = new HashMap();
	private final Map<String, MeetingActorImpl> kfActors = new HashMap();

	
	@Override
	public final String getId() { return mId; }

	@Override
	public MeetingActorImpl createActor(String userId,String userType) throws Exception
	{
		MeetingActorImpl actor;
		synchronized(this.mActors)
		{
			actor = this.mActors.get(userId);
			if(actor == null)
			{
				actor = new MeetingActorImpl(this, userId,userType);
				this.mActors.put(userId, actor);
			}
		}
		return actor;
	}

	@Override
	public void closedActor(String userId, String userType) {
		MeetingActorImpl actor;
		synchronized(this.mActors)
		{
			actor = this.mActors.get(userId);
			if(actor != null)
			{
			this.mActors.remove(userId);
			}
		}
		//return actor;
	}

	@Override
	public MeetingActor getActor(String actorId) throws Exception
	{
		MeetingActor actor;
		synchronized(this.mActors)
		{
			actor = this.mActors.get(actorId);
		}
		return actor;
	}

	@Override
	public Map<String, MeetingActorImpl> getActorAll() throws Exception {
		Map<String, MeetingActorImpl> mActors = this.mActors;
		return mActors;
	}

	private long mSerialSeed = 0; //消息流水号的种子

	/**
	 * 取下一个可用的消息流水号
	 * @return 消息流水号
	 */
	public final long nextSerial()
	{
		synchronized(this)
		{
			return ++ this.mSerialSeed;
		}
	}

}
