package carsharing.model

import org.junit.jupiter.api.Test
import spock.lang.Specification

class CompanyTest extends Specification {
    static final int ID = 11
    static final def NAME = "Рога и Копыта"

    Company underTest

    void setup() {
        underTest = new Company()
        underTest.id = ID
        underTest.name = NAME
    }

    @Test
    def "GetId"() {
        expect:
        underTest.getId() == ID
        underTest.getName() == NAME
    }

    def "GetName"() {
    }

    def "SetId"() {
    }

    def "SetName"() {
    }
}
