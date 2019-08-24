<?php

namespace sitato\Http\Controllers;

use sitato\role;
use Illuminate\Http\Request;

class roleController extends Controller
{
    public function index() 
    {
        $data= role::all();
        return response()->json($data, 200);
    }

    public function create()
    {
        //
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new role;
        $data->nama_role= $request->nama_role;
        
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    public function show($id) 
    {
        $data= role::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(role $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= role::find($id);
        if(!is_null($request->nama_role))
        {
            $data->nama_role= $request->nama_role;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    public function destroy($id)
    {
        $data= role::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
