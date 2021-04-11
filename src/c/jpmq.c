/**
 * @file JPMQ.c
 * @brief Native functions for JPMQ
 * @author Adam Bruce
 * @date 08 Apr 2021
 */

#include <jni.h>
#include <mqueue.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <fcntl.h>
#include <stdio.h>
#include <errno.h>

#include "../../tmp/net_adambruce_jpmq_JPMQ.h"

/**
 * Parses a JPMQAttributes object into a mq_attr struct.
 *
 * @param attr pointer to the mq_attr struct
 * @param jpmq_attr pointer to the JPMQAttributes object
 * @param env pointer to the JNI environment
 */
void parse_jpmq_attr(struct mq_attr *attr, jobject jpmq_attr, JNIEnv *env)
{
  jclass Jpmq_attr;
  jfieldID mq_flags_id, mq_maxmsg_id, mq_msgsize_id, mq_curmsgs_id;

  Jpmq_attr      = (*env)->GetObjectClass(env, jpmq_attr);
  mq_flags_id   = (*env)->GetFieldID(env, Jpmq_attr, "flags", "I");
  mq_maxmsg_id  = (*env)->GetFieldID(env, Jpmq_attr, "maxMessages", "I");
  mq_msgsize_id = (*env)->GetFieldID(env, Jpmq_attr, "messageSize", "I");
  mq_curmsgs_id = (*env)->GetFieldID(env, Jpmq_attr, "currentMessages", "I");

  attr->mq_flags   = (*env)->GetIntField(env, jpmq_attr, mq_flags_id);
  attr->mq_maxmsg  = (*env)->GetIntField(env, jpmq_attr, mq_maxmsg_id);
  attr->mq_msgsize = (*env)->GetIntField(env, jpmq_attr, mq_msgsize_id);
  attr->mq_curmsgs = (*env)->GetIntField(env, jpmq_attr, mq_curmsgs_id);
}

/**
 * Parses a JPMQAttributes object into a mq_attr struct.
 *
 * @param tpsec pointer to the timespec struct
 * @param jpmq_timespec pointer to the JPMQTimespec object
 * @param env pointer to the JNI environment
 */
void parse_jpmq_timespec(struct timespec *tspec, jobject jpmq_timespec,
						JNIEnv *env)
{
  jclass Jpmq_timespec;
  jfieldID sec_id, nsec_id;

  Jpmq_timespec = (*env)->GetObjectClass(env, jpmq_timespec);
  sec_id       = (*env)->GetFieldID(env, Jpmq_timespec, "seconds", "I");
  nsec_id      = (*env)->GetFieldID(env, Jpmq_timespec, "nanoSeconds", "I");

  tspec->tv_sec = (*env)->GetIntField(env, jpmq_timespec, sec_id);
  tspec->tv_nsec = (*env)->GetIntField(env, jpmq_timespec, nsec_id);
}

jbyteArray to_universal_mqd_t(mqd_t mqdes, JNIEnv *env)
{
	jbyte byte_buf[8];
	jbyteArray byte_arr;
	memcpy(byte_buf, &mqdes, sizeof(mqd_t));
	byte_arr = (*env)->NewByteArray(env, 8);
	(*env)->SetByteArrayRegion(env, byte_arr, 0, 8, byte_buf);
	return byte_arr;
}

mqd_t from_universal_mqd_t(jbyteArray unimqdes, JNIEnv *env)
{
	jbyte byte_arr[8];
	mqd_t mqdes;
	(*env)->GetByteArrayRegion(env, unimqdes, 0, 8, byte_arr);
	memcpy(&mqdes, byte_arr, sizeof(mqd_t));
	return mqdes;
}

int parse_jpmq_flags(jint oflag)
{
	int flags;
	flags = 0;
	if(oflag & net_adambruce_jpmq_JPMQ_O_RDONLY)   flags |= O_RDONLY;
	if(oflag & net_adambruce_jpmq_JPMQ_O_WRONLY)   flags |= O_WRONLY;
	if(oflag & net_adambruce_jpmq_JPMQ_O_RDWR)     flags |= O_RDWR;
	if(oflag & net_adambruce_jpmq_JPMQ_O_CLOEXEC)  flags |= O_CLOEXEC;
	if(oflag & net_adambruce_jpmq_JPMQ_O_CREAT)    flags |= O_CREAT;
	if(oflag & net_adambruce_jpmq_JPMQ_O_EXCL)     flags |= O_EXCL;
	if(oflag & net_adambruce_jpmq_JPMQ_O_NONBLOCK) flags |= O_NONBLOCK;
	return flags;
}

/**
 * Implementation for the JPMQ::nativeOpen method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param name the name of the message queue
 * @param oflag the flags for opening the message queue
 * @returns the return value of mq_open
 */
JNIEXPORT jbyteArray JNICALL Java_net_adambruce_jpmq_JPMQ_nativeOpen
  (JNIEnv *env, jobject obj, jstring name, jint oflag)
{
  const char *mq_name;
  mqd_t mqdes;
  int flags;

  flags = parse_jpmq_flags(oflag);
  mq_name = (*env)->GetStringUTFChars(env, name, NULL);
  mqdes = mq_open(mq_name, flags);
  return to_universal_mqd_t(mqdes, env);
}

/**
 * Implementation for the JPMQ::nativeOpenWithAttributes method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param name the name of the message queue
 * @param oflag the flags for opening the message queue
 * @returns the return value of mq_open
 */
JNIEXPORT jbyteArray JNICALL Java_net_adambruce_jpmq_JPMQ_nativeOpenWithAttributes
(JNIEnv *env, jobject obj, jstring name, jint oflag, jint mode,
 jobject jpmq_attr)
{
	const char *mq_name;
	mqd_t mqdes;
	struct mq_attr mq_attrs;
	int flags;

	flags = parse_jpmq_flags(oflag);
	mq_name = (*env)->GetStringUTFChars(env, name, NULL);
	parse_jpmq_attr(&mq_attrs, jpmq_attr, env);
	mqdes = mq_open(mq_name, flags, mode, &mq_attrs);
	return to_universal_mqd_t(mqdes, env);
}

/**
 * Implementation for the JPMQ::nativeClose method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @returns the return value of mq_close
 */
JNIEXPORT jint JNICALL Java_net_adambruce_jpmq_JPMQ_nativeClose
(JNIEnv *env, jobject obj, jbyteArray mqdes)
{
	mqd_t unimqdes;

	unimqdes = from_universal_mqd_t(mqdes, env);
	return mq_close(unimqdes);
}

/**
 * Implementation for the JPMQ::nativeUnlink method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param name the name of the message queue
 * @returns the return value of mq_unlink
 */
JNIEXPORT jint JNICALL Java_net_adambruce_jpmq_JPMQ_nativeUnlink
(JNIEnv *env, jobject obj, jstring name)
{
  const char *mq_name;

  mq_name = (*env)->GetStringUTFChars(env, name, NULL);
  return mq_unlink(mq_name);
}

/**
 * Implementation for the JPMQ::nativeGetAttributes method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @returns a JPMQAttributess object containing the message queue's attributes.
 */
JNIEXPORT jobject JNICALL Java_net_adambruce_jpmq_JPMQ_nativeGetAttributes
(JNIEnv *env, jobject obj, jbyteArray mqdes)
{
  struct mq_attr attr;
  mqd_t unimqdes;
  jclass jpmq_attr_class;
  jmethodID constructor;
  jobject jpmq_attr_obj;

  unimqdes = from_universal_mqd_t(mqdes, env);

  mq_getattr(unimqdes, &attr);
  jpmq_attr_class = (*env)->FindClass(env, "JPMQAttributess");
  constructor = (*env)->GetMethodID(env, jpmq_attr_class, "<init>", "(IIII)V");
  jpmq_attr_obj = (*env)->NewObject(env, jpmq_attr_class, constructor,
				   attr.mq_flags,
				   attr.mq_maxmsg,
				   attr.mq_msgsize,
				   attr.mq_curmsgs);
  return jpmq_attr_obj;
}

/**
 * Implementation for the JPMQ::nativeSetAttributes method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @param jpmq_attr pointer to the JPMQAttributess object
 * @returns the return value of mq_setattr
 */
JNIEXPORT jint JNICALL Java_net_adambruce_jpmq_JPMQ_nativeSetAttributes
(JNIEnv *env, jobject obj, jbyteArray mqdes, jobject jpmq_attr)
{
  struct mq_attr attrs;
  mqd_t unimqdes;

  parse_jpmq_attr(&attrs, jpmq_attr, env);
  unimqdes = from_universal_mqd_t(mqdes, env);

  return mq_setattr(unimqdes, &attrs, NULL);
}

/**
 * Implementation for the JPMQ::nativeReceive method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @returns a String containing the next message in the queue
 */
JNIEXPORT jstring JNICALL Java_net_adambruce_jpmq_JPMQ_nativeReceive
(JNIEnv *env, jobject obj, jbyteArray mqdes)
{
  struct mq_attr attr;
  mqd_t unimqdes;
  char *buf;
  jstring str;

  unimqdes = from_universal_mqd_t(mqdes, env);

  mq_getattr(unimqdes, &attr);
  buf = (char*)malloc(attr.mq_msgsize + 1);
  memset(buf, '\0', attr.mq_msgsize + 1);
  mq_receive(unimqdes, buf, attr.mq_msgsize + 1, NULL);
  str = (*env)->NewStringUTF(env, buf);
  free(buf);
  return str;
}

/**
 * Implementation for the JPMQ::nativeSend method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @param msg the String to send
 * @param length the length of the message
 * @returns the return value of mq_send
 */
JNIEXPORT jint JNICALL Java_net_adambruce_jpmq_JPMQ_nativeSend
(JNIEnv *env, jobject obj, jbyteArray mqdes, jstring msg, jint length, jint priority)
{
  const char *msgbuf;
  mqd_t unimqdes;

  unimqdes = from_universal_mqd_t(mqdes, env);

  msgbuf = (*env)->GetStringUTFChars(env, msg, NULL);
  return mq_send(unimqdes, msgbuf, length, priority);
}

/**
 * Implementation for the JPMQ::nativeTimedReceive method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @param timespec pointer to the JPMQTimespec
 * @returns a String containing the next message in the queue
 */
JNIEXPORT jstring JNICALL Java_net_adambruce_jpmq_JPMQ_nativeTimedReceive
(JNIEnv *env, jobject obj, jbyteArray mqdes, jobject timespec)
{
  struct mq_attr attr;
  mqd_t unimqdes;
  struct timespec tspec;
  char *buf;
  jstring str;

  unimqdes = from_universal_mqd_t(mqdes, env);

  parse_jpmq_timespec(&tspec, timespec, env);
  mq_getattr(unimqdes, &attr);
  buf = (char*)malloc(attr.mq_msgsize + 1);
  memset(buf, '\0', attr.mq_msgsize + 1);
  mq_timedreceive(unimqdes, buf, attr.mq_msgsize + 1, NULL, &tspec);
  str = (*env)->NewStringUTF(env, buf);
  free(buf);
  return str;
}

/**
 * Implementation for the JPMQ::nativeTimedSend method.
 *
 * @param env pointer to the JNI environment
 * @param obj pointer to the JPMQ object
 * @param mqdes the message queue descriptor
 * @param msg the String to send
 * @param length the length of the message
 * @returns the return value of mq_timedsend
 */
JNIEXPORT jint JNICALL Java_net_adambruce_jpmq_JPMQ_nativeTimedSend
(JNIEnv *env, jobject obj, jbyteArray mqdes, jstring msg, jint length, jint priority,
 jobject timespec)
{
  struct timespec tspec;
  mqd_t unimqdes;
  const char *msgbuf;

  unimqdes = from_universal_mqd_t(mqdes, env);

  parse_jpmq_timespec(&tspec, timespec, env);
  msgbuf = (*env)->GetStringUTFChars(env, msg, NULL);
  return mq_timedsend(unimqdes, msgbuf, length, priority, &tspec);
}
