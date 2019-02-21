<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Folder extends Model
{
    public function user()
    {
        return $this->belongsTo('App\User');
    }

    protected $fillable = [
        'name','user_id'
    ];
}
