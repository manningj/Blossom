package bruteforce;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import bruteforce.business.AccessAccountMockTest;
import bruteforce.business.AccessAccountTest;
import bruteforce.business.AccessPlantTest;
import bruteforce.business.AccessTaskMockTest;
import bruteforce.business.AccessTaskTest;
import bruteforce.business.CalculatePointsTest;
import bruteforce.objects.AccountTest;
import bruteforce.objects.PlantTest;
import bruteforce.objects.TaskTest;


/**
 Class: AllUnitTests
 Author: Triet Nguyen
 Purpose: This class executes all object test class automatically.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessAccountTest.class,
        AccessTaskTest.class,
        CalculatePointsTest.class,
        AccountTest.class,
        PlantTest.class,
        TaskTest.class,
        AccessPlantTest.class,
        AccessAccountMockTest.class,
        AccessTaskMockTest.class
})


public class AllUnitTests {

}

