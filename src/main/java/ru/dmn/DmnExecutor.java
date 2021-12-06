package ru.dmn;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Map;

public class DmnExecutor {
  private final DMNModel dmnModel;
  private final DMNContext dmnContext;
  private final DMNRuntime dmnRuntime;

  private static final String NAMESPACE = "https://kiegroup.org/dmn/_CD53848C-23A9-471E-B638-BA3A4E8BD918";
  private static final String MODEL_NAME = "Выход на пенсию по возрасту";

  public DmnExecutor() {
    final KieServices kieServices = KieServices.Factory.get();
    final ReleaseId releaseId = kieServices.newReleaseId("com.example", "test", "0.0.1-SNAPSHOT");
    final KieFileSystem kfs = kieServices.newKieFileSystem();

    // копируем в ресурсы DMN модель
    final File dmn = new File(
        "C:\\java\\job\\pfr\\hbase\\src\\main\\resources\\dmn\\pension.dmn"); //todo: очевидно, что через JAR это не
    // будет работать
    try {
      kfs.write("src/main/resources/dmn/Выход на пенсию по возрасту.dmn", Files.readAllBytes(dmn.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // создаем и сохраняем kmodule.xml
    final KieModuleModel kModuleModel = kieServices.newKieModuleModel();
    kfs.writeKModuleXML(kModuleModel.toXML());
    kfs.generateAndWritePomXML(releaseId); // генерим pom.xml

    final KieBuilder kb = kieServices.newKieBuilder(kfs);
    kb.buildAll();

    final KieContainer kieContainer = kieServices.newKieContainer(releaseId);

    this.dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
    this.dmnModel = dmnRuntime.getModel(NAMESPACE, MODEL_NAME);
    this.dmnContext = dmnRuntime.newContext();
  }

  //сделать через DTO и генерики
  public DMNResult execute(final Map<String, Object> input) {
//    var definition = CallFunctionFn.Graph.GetDefinition(executionDefinitionId);

    dmnContext.set("Человек", input);
    dmnContext.set("Дата расчета", LocalDate.of(2021, 9, 28));
    dmnContext.set("Количество воспитанных детей", 5);
    return dmnRuntime.evaluateAll(dmnModel, dmnContext);
  }
}
