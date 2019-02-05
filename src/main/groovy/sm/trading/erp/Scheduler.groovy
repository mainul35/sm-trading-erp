package sm.trading.erp

class Scheduler {
    static schedule(Closure c){
        c.call()
    }
}
