import csv
import numpy as np
import qpython 
from qpython import qconnection
from qpython.qcollection import qlist
from qpython.qtype import QException, QTIME_LIST, QSYMBOL_LIST, QFLOAT_LIST, QLONG_LIST

with qconnection.QConnection(host='localhost', port=5010) as q:
    with open("/home/woliver_kx_com/Advanced_KDB_CMTP/Q3/pythonAPI", "r") as f:
        reader = csv.reader(f, delimiter=",")
        for line in enumerate(reader):
            if (line[1][0] == 'time'):
                continue
            q.sendSync(('.u.upd[`trade;(`{sym};{price};{size})]').format(sym=line[1][1],price=float(line[1][2]),size=int(line[1][3])))
