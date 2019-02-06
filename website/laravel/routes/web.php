<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
Route::middleware(['auth' ])->group(function () {
    
    Route::get('/', 'ToDoController@index');

    Route::post('/store','ToDoController@store')->name('store');
    Route::get('/edit/{id}','ToDoController@edit')->name('edit');
    Route::post('/update/{id}','ToDoController@update')->name('update');
    Route::get('/delete/{id}','ToDoController@delete')->name('delete');

});




Auth::routes();

