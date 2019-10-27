package com.indieprogress.shopinglisttest.di.application;

import android.app.Application;
import com.indieprogress.shopinglisttest.MyApp;
import com.indieprogress.shopinglisttest.data.RepositoryModule;
import com.indieprogress.shopinglisttest.di.ActivityBindingModule;
import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent extends AndroidInjector<MyApp> {
    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);
        ApplicationComponent build();
    }
}
