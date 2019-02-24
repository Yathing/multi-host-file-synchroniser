<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;

class Folder extends Model
{   
    use Notifiable;
    
    public function user()
    {
        return $this->belongsTo('App\User');
    }
   

    protected $fillable = [
        'name','user_id'
    ];
}
