package controller.base

import domain.services.base.BaseService

interface ControllerBase<T : BaseService<*>> {
    var service: T
}