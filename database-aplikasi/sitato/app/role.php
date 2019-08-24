<?php

namespace sitato;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class role extends Model
{
    protected $table= 'role';
    protected $primaryKey= 'id_role';
    public $timestamps= true;
    use SoftDeletes;
    protected $fillable= [
        'nama_role'
    ];
}
