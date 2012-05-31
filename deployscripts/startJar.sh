#!/bin/sh
JAVAPATH=/usr/bin/java
TARGETFILE=${1}
PIDFILE=MIConnector.pid
PID=0
FORCERESTART=0

removePIDFile() {
  unlink ${PIDFILE}
}

startProgram() {
  nohup ${JAVAPATH} -jar ${TARGETFILE} >/dev/null 2>&1 &
  if [ $? -eq 0 ] ; then
    NEWPID=$!
    echo "startProgram() started program ${NEWPID}"
    echo ${NEWPID} > ${PIDFILE}
  else
    echo "startProgram() program failed to start :("
    exit 1
  fi
}

killProgram() {
  echo "killProgram() trying to kill program '${PID}' ..."
  kill ${PID}
  sleep 10s
  echo "killProgram() waiting for program shutdown..."
  kill -0 ${PID} 2>/dev/null
  RET=$?
  if [ ${RET} -eq 0 ] ; then
    echo "killProgram() program still running, using -9 to kill!"
    kill -9 ${PID}
    sleep 5s
  fi
}

if [ -z ${TARGETFILE} ] ; then
  echo "ERROR: no targetfile supplied!"
  exit 1
fi

if [ ! -e ${TARGETFILE} ] ; then
  echo "ERROR: given target file doees not exists!"
  exit 1
fi

if [ ! -z ${2} ] ; then
  FORCERESTART=1
fi

if [ -e ${PIDFILE} ] ; then
  PID=`cat ${PIDFILE}`
  if ! [[ "${PID}" =~ ^[0-9]+$ ]] ; then
    echo "pidfile doesnt hold a number in it!"
    removePIDFile
    startProgram
    exit 0
  fi
else
  echo "no pidfile exists, we just start our program then!"
  startProgram
fi

if [ -n ${PID} -a ${PID} -gt 0 ] ; then
  kill -0 ${PID} 2>/dev/null
  RET=$?
  if [ ${RET} -eq 0 ] ; then
    if [ ${FORCERESTART} -gt 0 ] ; then
      echo "program is already running! but force restart is set >0, restarting..."
      killProgram
      startProgram
      exit 0
    else
      echo "program is already running!"
      exit 0
    fi
  else
    removePIDFile
    startProgram
    exit 0
  fi
fi

echo "no function matched, this start script is borked!"
exit 1
