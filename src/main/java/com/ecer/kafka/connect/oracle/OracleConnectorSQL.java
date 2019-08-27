package com.ecer.kafka.connect.oracle;

public interface OracleConnectorSQL{
    String LOGMINER_SELECT_WITHSCHEMA="SELECT thread#, scn, start_scn, commit_scn,timestamp, operation_code, operation,status, SEG_TYPE_NAME ,info,seg_owner, table_name, username, sql_redo ,row_id, csf, TABLE_SPACE, SESSION_INFO, RS_ID, RBASQN, RBABLK, SEQUENCE#, TX_NAME, SEG_NAME, SEG_TYPE_NAME FROM  v$logmnr_contents  WHERE OPERATION_CODE in (1,2,3) and commit_scn>=? and ";
//    String LOGMINER_SELECT_WITHSCHEMA="select SQL_REDO from V$LOGMNR_CONTENTS  WHERE OPERATION_CODE in (1,2,3) and commit_scn>=? and ";
//    String START_LOGMINER_CMD="begin \nDBMS_LOGMNR.START_LOGMNR(STARTSCN => ?,";
    String START_LOGMINER_CMD="begin \n dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo01.log',options=>DBMS_LOGMNR.NEW); \n" +
        " dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo02.log',options=>DBMS_LOGMNR.ADDFILE); \n" +
        " dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo03.log',options=>DBMS_LOGMNR.ADDFILE); \n" +
        " DBMS_LOGMNR.START_LOGMNR(STARTSCN => ?,";
//    String START_LOGMINER_CMD="begin \nDBMS_LOGMNR.START_LOGMNR( ";
//    String START_LOGMINER_CMD="begin \n dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo01.log',options=>DBMS_LOGMNR.NEW); \n DBMS_LOGMNR.START_LOGMNR( ";
//    String LOGMINER_START_OPTIONS="OPTIONS =>  DBMS_LOGMNR.SKIP_CORRUPTION+DBMS_LOGMNR.NO_SQL_DELIMITER+DBMS_LOGMNR.NO_ROWID_IN_STMT+DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG + DBMS_LOGMNR.CONTINUOUS_MINE+DBMS_LOGMNR.COMMITTED_DATA_ONLY+dbms_logmnr.STRING_LITERALS_IN_STMT );\n end;";
    String LOGMINER_START_OPTIONS="OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG+ DBMS_LOGMNR.COMMITTED_DATA_ONLY );\n end; \n ";
    String STOP_LOGMINER_CMD="begin \nSYS.DBMS_LOGMNR.END_LOGMNR; \nend;";
    String CURRENT_DB_SCN_SQL = "select min(current_scn) CURRENT_SCN from gv$database";    
    String LASTSCN_STARTPOS = "select min(FIRST_CHANGE#) FIRST_CHANGE# from (select FIRST_CHANGE# from v$log where ? between FIRST_CHANGE# and NEXT_CHANGE# union select FIRST_CHANGE# from v$archived_log where ? between FIRST_CHANGE# and NEXT_CHANGE# and standby_dest='NO')";
    String TABLE_WITH_COLS ="with dcc as (SELECT dcc.owner,dcc.table_name,dcc2.column_name,1 PK_COLUMN from dba_constraints dcc,dba_cons_columns dcc2 where dcc.owner=dcc2.owner and dcc.table_name=dcc2.table_name and dcc.constraint_name=dcc2.constraint_name and dcc.constraint_type='P'),duq as (select di2.TABLE_OWNER,di2.TABLE_NAME,di2.COLUMN_NAME , 1 UQ_COLUMN from dba_ind_columns di2 join dba_indexes di on di.table_owner=di2.TABLE_OWNER and di.table_name=di2.TABLE_NAME and di.uniqueness='UNIQUE' and di.owner=di2.INDEX_OWNER and di.index_name=di2.INDEX_NAME group by di2.TABLE_OWNER,di2.TABLE_NAME,di2.COLUMN_NAME) select dc.owner,dc.TABLE_NAME,dc.COLUMN_NAME,dc.NULLABLE,dc.DATA_TYPE,nvl(dc.DATA_PRECISION,dc.DATA_LENGTH) DATA_LENGTH,nvl(dc.DATA_SCALE,0) DATA_SCALE,nvl(dc.DATA_PRECISION,0) DATA_PRECISION,nvl(x.pk_column,0) pk_column,nvl(y.uq_column,0) uq_column from dba_tab_cols dc left outer join dcc x on x.owner=dc.owner and x.table_name=dc.TABLE_NAME and dc.COLUMN_NAME=x.column_name left outer join duq y on y.table_owner=dc.owner and y.table_name=dc.TABLE_NAME and y.column_name=dc.COLUMN_NAME where dC.Owner='$TABLE_OWNER$' and dc.TABLE_NAME='$TABLE_NAME$' and dc.HIDDEN_COLUMN='NO' and dc.VIRTUAL_COLUMN='NO' order by dc.TABLE_NAME,dc.COLUMN_ID";

    String LOGMINER = "begin \n dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo01.log',options=>DBMS_LOGMNR.NEW); \n" +
            " dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo02.log',options=>DBMS_LOGMNR.ADDFILE); \n" +
            " dbms_logmnr.add_logfile(logfilename=>'/home/oracle/data.ora/hiveOracle/redo03.log',options=>DBMS_LOGMNR.ADDFILE); \n" +
            " DBMS_LOGMNR.START_LOGMNR(STARTSCN => 0,OPTIONS => DBMS_LOGMNR.DICT_FROM_ONLINE_CATALOG+ DBMS_LOGMNR.COMMITTED_DATA_ONLY );\n end; \n";

}