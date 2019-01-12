package sm.trading.erp

class ManagementController {

    def dashboard() {
        render(view: 'dashboard', model: [name: 'Mainul'])
    }
}
